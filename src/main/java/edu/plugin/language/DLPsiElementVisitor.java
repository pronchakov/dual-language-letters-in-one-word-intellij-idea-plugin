package edu.plugin.language;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralValue;
import edu.plugin.language.lang.Language;
import edu.plugin.language.lang.LanguagesFactory;
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
        if (rawText.length() == 0 || rawText.isBlank() || !rawText.startsWith("\"") || "\"\"".equals(rawText)) {
            return;
        }
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
        final var languages = LanguagesFactory.getRegisteredLanguages();
        for (char letter: letters) {
            for (Language language : languages) {
                if ((letter >= language.getFirstLower() && letter <= language.getLastLower()) || (letter >= language.getFirstUpper() && letter <= language.getLastUpper())) {
                    result.set(language);
                    break;
                }
            }
        }
        return result;
    }

    private boolean hasDifferentLanguages(Result result) {
        final var letterLanguages = result.getLetters();
        if (letterLanguages.isEmpty()) {
            return false;
        }
        final var firstLetterLanguage = letterLanguages.get(0);
        for (int i = 1; i < letterLanguages.size(); i++) {
            final var nextLetterLanguage = letterLanguages.get(i);
            if (nextLetterLanguage != null && !Objects.equals(firstLetterLanguage, nextLetterLanguage)) {
                return true;
            }
        }
        return false;
    }

    private void highlightWord(PsiElement element, int wordPosition, Result result) {
        final var augmentedWord = new StringBuilder();
        final var letterLanguages = result.getLetters();
        final var letters = result.getWord().toCharArray();
        for (int i = 0; i < letterLanguages.size(); i++) {
            augmentedWord.append(letters[i]);
            augmentedWord.append("(");
            if (letterLanguages.get(i) != null) {
                augmentedWord.append(letterLanguages.get(i).getName());
            }
            augmentedWord.append(")");
        }
        holder.registerProblem(
                element,
                "Word contains letters from different languages: " + augmentedWord,
                ProblemHighlightType.GENERIC_ERROR,
                TextRange.from(wordPosition, letters.length)
        );
    }

}
