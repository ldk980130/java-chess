package chess.webcontroller;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chess.domain.ChessGame;
import chess.domain.command.Command;
import chess.domain.command.Start;
import chess.repository.ChessGameRepository;
import chess.service.ChessGameService;
import chess.converter.RequestToCommandConverter;
import chess.converter.RequestToMapConverter;
import chess.webcontroller.dto.BoardResponseDto;
import chess.webcontroller.dto.GameResponseDto;
import chess.webcontroller.dto.NameResponseDto;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class WebController {

	private static final int PORT_NUMBER = 8081;
	private static final String FILE_LOCATION = "/static";

	private static final String MAIN_PAGE = "index.html";
	private static final String GAME_PAGE = "game.html";

	private static final String PATH_VARIABLE_NAME = ":name";
	private static final String NAMES = "names";
	private static final String NAME = "name";

	private final ChessGameService gameService;

	public WebController() {
		this.gameService = new ChessGameService(new ChessGameRepository());
	}

	public void run() {
		port(PORT_NUMBER);
		staticFileLocation(FILE_LOCATION);

		showMain(new HashMap<>());
		enterNewGame(new HashMap<>());
		startGame(new HashMap<>());
		continueGame(new HashMap<>());
		deleteGame();
		move(new HashMap<>());

		exception(Exception.class, (exception, request, response) ->
			response.body(exception.getMessage()));
	}

	private void showMain(Map<String, Object> model) {
		get("/", (req, res) -> {
			List<NameResponseDto> nameDtos = gameService.findAllGames().stream()
				.map(NameResponseDto::new)
				.collect(Collectors.toList());
			model.put(NAMES, nameDtos);
			return render(model, MAIN_PAGE);
		});
	}

	private void enterNewGame(Map<String, Object> model) {
		post("/new_game", (request, response) -> {
			Map<String, String> name = RequestToMapConverter.ofSingle(request);

			ChessGame game = gameService.createGame(name.get(NAME));

			fillModelEmptyBoard(model, game);
			return render(model, GAME_PAGE);
		});
	}

	private void startGame(Map<String, Object> model) {
		get("/start/:name", (request, response) -> {
			ChessGame updatedGame = gameService.updateGame(new Start(), request.params(PATH_VARIABLE_NAME));
			model.putAll(new GameResponseDto(updatedGame).getValue());
			return render(model, GAME_PAGE);
		});
	}

	private void continueGame(Map<String, Object> model) {
		get("/:name", (request, response) -> {
			ChessGame findGame = gameService.findGame(request.params(PATH_VARIABLE_NAME));
			if (findGame.isReady()) {
				fillModelEmptyBoard(model, findGame);
				return render(model, GAME_PAGE);
			}
			model.putAll(new GameResponseDto(findGame).getValue());
			return render(model, GAME_PAGE);
		});
	}

	private void fillModelEmptyBoard(Map<String, Object> model, ChessGame game) {
		model.putAll(BoardResponseDto.empty().getValue());
		model.put(NAME, game.getName());
	}

	private void move(Map<String, Object> model) {
		post("/move/:name", (request, response) -> {
			Command command = RequestToCommandConverter.from(request);
			ChessGame updatedGame = gameService.updateGame(command, request.params(PATH_VARIABLE_NAME));

			if (updatedGame.isFinished()) {
				response.redirect("/");
				return null;
			}

			model.putAll(new GameResponseDto(updatedGame).getValue());
			return render(model, GAME_PAGE);
		});
	}

	private void deleteGame() {
		post("/delete/:name", (request, response) -> {
			gameService.deleteGame(request.params(PATH_VARIABLE_NAME));
			response.redirect("/");
			return null;
		});
	}

	private String render(Map<String, Object> model, String templatePath) {
		return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
	}
}
