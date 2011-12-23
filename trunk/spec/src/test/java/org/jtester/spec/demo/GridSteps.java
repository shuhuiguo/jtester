package org.jtester.spec.demo;

import org.hamcrest.core.IsEqual;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jtester.spec.demo.gameoflife.Game;
import org.jtester.spec.demo.gameoflife.StringRenderer;
import org.junit.Assert;

public class GridSteps {
	private Game game;
	private StringRenderer renderer;

	@Given("a $width by $height game")
	public void theGameIsRunning(int width, int height) {
		game = new Game(width, height);
		renderer = new StringRenderer();
		game.setObserver(renderer);
	}

	@When("I toggle the cell at ($column, $row)")
	@Aliases(values = { "我将游标移到位置($column, $row)" })
	public void iToggleTheCellAt(int column, int row) {
		game.toggleCellAt(column, row);
	}

	@Then("the grid should look like $grid")
	@Aliases(values = { "the grid should be $grid",// <br>
			"输出文本结果如下 $grid" })
	public void theGridShouldLookLike(String grid) {
		String result = renderer.asString();
		Assert.assertThat(result, IsEqual.equalTo(grid));
	}
}
