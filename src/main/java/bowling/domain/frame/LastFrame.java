package bowling.domain.frame;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.LinkedList;

import bowling.domain.Index;
import bowling.domain.Pins;
import bowling.domain.state.Miss;
import bowling.domain.state.Ready;
import bowling.domain.state.State;

public class LastFrame implements Frame {
	private static final int MIN_OF_BOWL = 2;
	private static final int MAX_OF_BOWL = 3;

	private final LinkedList<State> states;
	private int count;

	private LastFrame() {
		this.states = new LinkedList<>(Collections.singletonList(Ready.create()));
		this.count = 0;
	}

	public static LastFrame initialize() {
		return new LastFrame();
	}

	@Override
	public Frame bowl(Pins pins) {
		count++;

		State last = states.getLast();

		if (last.isEnd()) {
			addNewStatus(pins);
			return this;
		}

		changeLastStatus(pins, last);
		return this;
	}

	private void addNewStatus(Pins pins) {
		State ready = Ready.create();
		states.add(ready.bowl(pins));
	}

	private void changeLastStatus(Pins pins, State last) {
		states.removeLast();
		states.add(last.bowl(pins));
	}

	@Override
	public boolean isEnd() {
		if (count == MAX_OF_BOWL) {
			return true;
		}
		return count == MIN_OF_BOWL && states.getLast() instanceof Miss;
	}

	@Override
	public int getFrameIndex() {
		return Index.MAX_OF_INDEX;
	}

	@Override
	public String symbol() {
		return states.stream()
			.map(State::symbol)
			.collect(joining("|"));
	}

	protected int size() {
		return states.size();
	}
}
