package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private CategoryRepository categoryRepository;
	
	private RecipeRepository recipeRepository;
	
	private UnitOfMeasureRepository uomRepository;
	

	public DataLoader(CategoryRepository categoryRepository,
			RecipeRepository recipeRepository,
			UnitOfMeasureRepository uomRepository) {
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
	}
	
	
	private List<Recipe> getRecipes() {
		List<Recipe> recipesList = new ArrayList<>();
		
		//get UOMs
		//EACH
		Optional<UnitOfMeasure> eachUomOpt = uomRepository.findByDescription("Each");
		if(!eachUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		//TABLESPOON
		Optional<UnitOfMeasure> tableSpoonUomOpt = uomRepository.findByDescription("Tablespoon");
		if(!tableSpoonUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		//TEASPOON
		Optional<UnitOfMeasure> teaSpoonUomOpt = uomRepository.findByDescription("Teaspoon");
		if(!teaSpoonUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		//DASH
		Optional<UnitOfMeasure> dashUomOpt = uomRepository.findByDescription("Dash");
		if(!dashUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		//PINT
		Optional<UnitOfMeasure> pintUomOpt = uomRepository.findByDescription("Pint");
		if(!pintUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		//CUP
		Optional<UnitOfMeasure> cupUomOpt = uomRepository.findByDescription("Cup");
		if(!cupUomOpt.isPresent())
			throw new RuntimeException("Unit of measure not found");
		
		UnitOfMeasure eachUom = eachUomOpt.get();
		UnitOfMeasure tableSpoonUom = tableSpoonUomOpt.get();
		UnitOfMeasure teaSpoonUom = teaSpoonUomOpt.get();
		UnitOfMeasure dashUom = dashUomOpt.get();
		UnitOfMeasure pintUom = pintUomOpt.get();
		UnitOfMeasure cupUom = cupUomOpt.get();
		
		//get categories
		Optional<Category> americanCategoryOpt = categoryRepository.findByDescription("American");
		if(!americanCategoryOpt.isPresent())
			throw new RuntimeException("Category not found");
		
		Optional<Category> mexicanCategoryOpt = categoryRepository.findByDescription("Mexican");
		if(!mexicanCategoryOpt.isPresent())
			throw new RuntimeException("Category not found");
		
		Category americanCategory = americanCategoryOpt.get();
		Category mexicanCategory = mexicanCategoryOpt.get();
		
		
		//guacamole
		Recipe guac = new Recipe();
		guac.setDescription("Perfect gucamole");
		guac.setPrepTime(10);
		guac.setCookTime(0);
		guac.setDifficulty(Difficulty.EASY);
		guac.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. "
				+ "Remove the pit. Score the inside of the avocado with a blunt knife "
				+ "and scoop out the flesh with a spoon. (See How to Cut and Peel an "
				+ "Avocado.) Place in a bowl. "
				+ "\n2 Mash with a fork: Using a fork, roughly mash the avocado. "
				+ "(Don't overdo it! The guacamole should be a little chunky.)"
				+ "\n3 Add salt, lime juice, and the rest: Sprinkle with salt and lime"
				+ " (or lemon) juice. The acid in the lime juice will provide some balance"
				+ " to the richness of the avocado and will help delay the avocados from"
				+ " turning brown." 
				+ "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers"
				+ " vary individually in their hotness. So, start with a half of one chili"
				+ " pepper and add to the guacamole to your desired degree of hotness. " 
				+ "Remember that much of this is done to taste because of the variability "
				+ "in the fresh ingredients. Start with this recipe and adjust to your "
				+ "taste."
				+ "\n4 Serve: Serve immediately, or if making a few hours ahead, place "
				+ "plastic wrap on the surface of the guacamole and press down to cover "
				+ "it and to prevent air reaching it. (The oxygen in the air causes "
				+ "oxidation which will turn the guacamole brown.) Refrigerate until "
				+ "ready to serve.");
		
		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes("Quick guacamole: For a very quick guacamole just take"
				+ " a 1/4 cup of salsa and mix it in with your mashed avocados.");
		
		guac.setNotes(guacNotes);
		
		guac.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom, guac));
		guac.addIngredient(new Ingredient("Kosher salt", new BigDecimal(0.5), teaSpoonUom, guac));
		guac.addIngredient(new Ingredient("fresh lime juice or lemon juice", 
				new BigDecimal(2), tableSpoonUom, guac));
		guac.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", 
				new BigDecimal(2), tableSpoonUom, guac));
		guac.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", 
				new BigDecimal(2), eachUom, guac));
		guac.addIngredient(new Ingredient("cilantro", new BigDecimal(2), tableSpoonUom, guac));
		guac.addIngredient(new Ingredient("reshly grated black pepper", 
				new BigDecimal(2), dashUom, guac));
		guac.addIngredient(new Ingredient("tomato, seeds and pulp removed, chopped", 
				new BigDecimal(0.5), eachUom, guac));
		
		guac.getCategories().add(americanCategory);
		guac.getCategories().add(mexicanCategory);
		
		recipesList.add(guac);
		
		return recipesList;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		recipeRepository.saveAll(getRecipes());
	}
}
