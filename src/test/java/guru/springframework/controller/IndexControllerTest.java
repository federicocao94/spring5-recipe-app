package guru.springframework.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.controllers.IndexController;
import guru.springframework.services.RecipeServiceImpl;

public class IndexControllerTest {
	
	@Mock
	private RecipeServiceImpl recipeService;
	
	@Mock
	private Model model;
	
	IndexController index;
	
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        index = new IndexController(recipeService);
    }
	
	
	@Test
	public void getIndexPage() throws Exception {        
        assertEquals(index.getIndexPage(model), "index");
        
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        verify(recipeService, times(1)).getRecipes();
	}
}
