package edu.plugin.language;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

public class DLPsiElementVisitor extends PsiElementVisitor {

    private ProblemsHolder holder;
    private Pattern pattern = Pattern.compile(" ");

    public DLPsiElementVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitElement(@NotNull PsiElement element) {
        if (!(element instanceof PsiLiteralValue)) {
            return;
        }
        final var rawText = element.getText();
        final var text = rawText.substring(1, rawText.length() - 1);
        for (String word : pattern.split(text)) {
            final var wordPosition = rawText.indexOf(word);
            final var result = groupLettersByLanguage(word);
            if (hasDifferentLanguages(result)) {
                highlightWord(element, wordPosition, result);
            }
        }
    }

    private Result groupLettersByLanguage(String word) {
        final var letters = word.toCharArray();
        final var result = new Result(word);
        final var languages = Language.values();
        for (var i = 0; i < letters.length; i++) {
            final var letter = letters[i];
            for (Language language : languages) {
                if ((letter >= language.getFirstLower() && letter <= language.getLastLower()) || (letter >= language.getFirstUpper() && letter <= language.getLastUpper())) {
                    result.set(i, language);
                    break;
                }
            }
        }
        return result;
    }

    private boolean hasDifferentLanguages(Result result) {
        final var letters = result.getLetters();
        final var firstLetter = letters[0];
        for (int i = 1; i < letters.length; i++) {
            final var nextLetter = letters[i];
            if (nextLetter != null && !Objects.equals(firstLetter, nextLetter)) {
                return true;
            }
        }
        return false;
    }

    private void highlightWord(PsiElement element, int wordPosition, Result result) {
        final var augmentedWord = new StringBuilder();
        final var letters = result.getLetters();
        final var chars = result.getWord().toCharArray();
        for (int i = 0; i < letters.length; i++) {
            augmentedWord.append(chars[i]);
            augmentedWord.append("(");
            if (letters[i] != null) {
                augmentedWord.append(letters[i].getTitle());
            }
            augmentedWord.append(")");
        }
        holder.registerProblem(
                element,
                "Word contains letters from different languages: " + augmentedWord,
                ProblemHighlightType.GENERIC_ERROR,
                TextRange.from(wordPosition, chars.length)
        );
    }

}
