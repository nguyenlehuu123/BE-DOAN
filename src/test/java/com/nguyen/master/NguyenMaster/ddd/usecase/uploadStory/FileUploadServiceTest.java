package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class FileUploadServiceTest {

    @Mock
    private MultipartFile mockFile1;

    @Mock
    private MultipartFile mockFile2;

    @InjectMocks
    private FileUploadService fileUploadService;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * should Upload Files When Directory Exists And Override Flag Is Set
     *
     * @throws IOException
     */
    @Test
    void shouldUploadFilesWhenDirectoryExistsAndOverrideFlagIsSet() throws IOException {
        String fileLocation = "existing_directory";
        Path fileLocationPath = Path.of(fileLocation);
        Files.createDirectories(fileLocationPath);

        when(mockFile1.getOriginalFilename()).thenReturn("file1.txt");
        when(mockFile2.getOriginalFilename()).thenReturn("file2.txt");

        fileUploadService.fileUpload(Arrays.asList(mockFile1, mockFile2), fileLocation, 1, 1);

        verify(mockFile1, times(1)).transferTo(any(File.class));
        verify(mockFile2, times(1)).transferTo(any(File.class));
        Files.deleteIfExists(fileLocationPath);
    }

    /**
     * should Throw Exception When Directory Does Not Exist And Auto Create Flag Is Not Set
     */
    @Test
    void shouldThrowExceptionWhenDirectoryDoesNotExistAndAutoCreateFlagIsNotSet() {
        String fileLocation = "non_existing_directory";

        assertThrows(Error400Exception.class, () -> {
            fileUploadService.fileUpload(Arrays.asList(mockFile1, mockFile2), fileLocation, 0, 1);
        });
    }

    /**
     * should Create Directory When Directory Does Not Exist And Auto Create Flag Is Set
     *
     * @throws IOException
     */
    @Test
    void shouldThrowExceptionWhenFileAlreadyExistsAndOverrideFlagIsNotSet() throws IOException {
        String fileLocation = "existing_directory";
        Path fileLocationPath = Path.of(fileLocation);
        Files.createDirectories(fileLocationPath);

        when(mockFile1.getOriginalFilename()).thenReturn("existing_file1.txt");
        when(mockFile2.getOriginalFilename()).thenReturn("existing_file2.txt");

        Files.createFile(fileLocationPath.resolve("existing_file1.txt"));
        Files.createFile(fileLocationPath.resolve("existing_file2.txt"));
        assertThrows(Error400Exception.class, () -> {
            fileUploadService.fileUpload(Arrays.asList(mockFile1, mockFile2), fileLocation, 1, 0);
        });
        Files.deleteIfExists(Path.of("existing_directory/existing_file1.txt"));
        Files.deleteIfExists(Path.of("existing_directory/existing_file2.txt"));
        Files.deleteIfExists(fileLocationPath);
    }

    /**
     * should Throw Exception When Creating Temp File Fails
     *
     * @throws IOException
     */
    @Test
    void shouldThrowExceptionWhenCreatingTempFileFails() throws IOException {
        String fileLocation = "existing_directory";
        Path fileLocationPath = Path.of(fileLocation);
        Files.createDirectories(fileLocationPath);

        when(mockFile1.getOriginalFilename()).thenReturn("file1.txt");
        doThrow(IOException.class).when(mockFile1).transferTo(any(File.class));

        assertThrows(Error400Exception.class, () -> {
            fileUploadService.fileUpload(Arrays.asList(mockFile1), fileLocation, 1, 1);
        });
        Files.deleteIfExists(fileLocationPath);
    }

    /**
     * should Throw Exception When Creating Directory Fails
     * @throws IOException
     */
    @Test
    void shouldThrowExceptionWhenCreatingDirectoryFails() throws IOException {
        String fileLocation = "existing_directory";
        when(mockFile1.getOriginalFilename()).thenReturn("file1.txt");

        Files.deleteIfExists(Path.of(fileLocation));
    }

    /**
     * should Throw Error400Exception When Creating Directory Fails
     * 
     * @throws IOException
     */
    @Test
    void shouldThrowError400ExceptionWhenCreatingDirectoryFails() throws IOException {
        String fileLocation = "existing_directory";
        Path fileLocationPath = Path.of(fileLocation);

        try (MockedStatic<Files> mocked = Mockito.mockStatic(Files.class)) {
            mocked.when(() -> Files.createDirectories(fileLocationPath)).thenThrow(IOException.class);

            assertThrows(Error400Exception.class, () -> {
                fileUploadService.fileUpload(Arrays.asList(mockFile1, mockFile2), fileLocation, 1, 1);
            });
        }
        Files.deleteIfExists(Path.of(fileLocation));
    }
}