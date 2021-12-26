package qna.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("delete_성공")
    public void delete_success() throws Exception {
        Answer answer = A1;
        answer.delete(UserTest.JAVAJIGI);
        Assertions.assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("delete_실패")
    public void delete_failed() {
        Answer answer = A1;
        Assertions.assertThatThrownBy(() -> answer.delete(UserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }
}
