package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileUploadService extends BaseService {
    public NormalDefaultResponse fileUpload(List<MultipartFile> lstFile, String fileLocation, Integer autoCreateFilePathFlg, Integer overrideExistedFileFlg) {
        Path fileLocationPath = Path.of(fileLocation);

        if (Files.exists(fileLocationPath)) {
            try {
                Path tempFile = Files.createTempFile(fileLocationPath, "", "");
                Files.delete(tempFile);
            } catch (IOException e) {
                List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
                throw new Error400Exception(Constants.E404, errorMessages);
            }
            if (overrideExistedFileFlg == 0) {
                String duplicateFileName = null;
                for (MultipartFile multipartFile : lstFile) {
                    String fileName = multipartFile.getOriginalFilename();
                    Path filePath = Paths.get(fileLocation, fileName);
                    File distionationFile = filePath.toFile();
                    if (distionationFile.exists()) {
                        if (StringUtils.isEmpty(duplicateFileName)) {
                            duplicateFileName = multipartFile.getOriginalFilename();
                        } else {
                            duplicateFileName = duplicateFileName + ", " + multipartFile.getOriginalFilename();
                        }
                    }
                }
                if (StringUtils.isNotEmpty(duplicateFileName)) {
                    List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
                    throw new Error400Exception(Constants.E404, errorMessages);
                }
            }
        } else {
            if (autoCreateFilePathFlg == 1) {
                try {
                    Files.createDirectories(fileLocationPath);
                } catch (IOException e) {
                    List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
                    throw new Error400Exception(Constants.E404, errorMessages);
                }
            } else {
                List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
                throw new Error400Exception(Constants.E404, errorMessages);
            }
        }


        for (MultipartFile multipartFile : lstFile) {
            String fileName = multipartFile.getOriginalFilename();
            Path filePath = Paths.get(fileLocation, fileName);
            File destinationFile = filePath.toFile();
            try {
                multipartFile.transferTo(destinationFile);
            } catch (IOException e) {
                List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
                throw new Error400Exception(Constants.E404, errorMessages);
            }
        }

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage("File upload success");
        return normalDefaultResponse;
    }
}
