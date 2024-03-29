package sfgcourse.recipeproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.exceptions.NotFoundException;
import sfgcourse.recipeproject.services.ImageService;
import sfgcourse.recipeproject.services.RecipeService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(recipeService, imageService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void testShowUploadImageForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        //when
        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipes/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    void testHandleImagePost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/recipes/1/image").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/1/show"))
                .andExpect(header().string("Location", "/recipes/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }

    @Test
    void testRenderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        String someBytes = "some bytes";
        Byte[] bytes = new Byte[someBytes.getBytes(StandardCharsets.UTF_8).length];

        int i = 0;
        for(byte b : someBytes.getBytes(StandardCharsets.UTF_8))
            bytes[i++] = b;

        recipeCommand.setImage(bytes);

        //when
        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipes/2/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        //then
        assertEquals(someBytes.getBytes(StandardCharsets.UTF_8).length, responseBytes.length);
    }

    @Test
    void testGetImageRecipeNotFoundException() throws Exception {
        when(recipeService.findCommandById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipes/5/image"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error404Page"));
    }

    //@Test
    void testGetImageRecipeNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipes/zyx/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error400Page"));
    }
}