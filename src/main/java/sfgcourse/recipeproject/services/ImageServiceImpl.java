package sfgcourse.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            try {
                Byte[] byteObjects = new Byte[file.getBytes().length];
                int i = 0;
                for (byte b : file.getBytes())
                    byteObjects[i++] = b;

                recipe.setImage(byteObjects);
                recipeRepository.save(recipe);
            } catch (IOException exception) {
                //todo handle exception
                log.error("Error occurred: ", exception);
                exception.printStackTrace();
            }
        } else {
            log.debug("Recipe id: " + recipeId + " not found.");
        }
        log.debug("Loaded new file.");
    }
}
