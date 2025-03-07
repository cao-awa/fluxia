package com.github.cao.awa.fluxia;

import com.github.cao.awa.translator.structuring.builtin.typescript.translate.kts.TypescriptKotlinScriptTranslator;
import com.github.cao.awa.translator.structuring.translate.StructuringTranslator;
import com.github.cao.awa.translator.structuring.translate.language.LanguageTranslateTarget;
import com.github.cao.awa.translator.structuring.translate.element.TranslateElementData;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluxiaMod implements ModInitializer {
    private final static Logger LOGGER = LogManager.getLogger("Fluxia");
    public static boolean enableDebug = false;

    @Override
    public void onInitialize() {
        LOGGER.info("Loading fluxia structuring translator {} provider '{}' for [typescript]", StructuringTranslator.getVersion(), StructuringTranslator.DEFAULT_PROVIDER);
        TypescriptKotlinScriptTranslator.postRegister();

        Map<LanguageTranslateTarget, Map<TranslateElementData<?>, StructuringTranslator<?>>> typescriptTranslators = StructuringTranslator.getTranslators(StructuringTranslator.DEFAULT_PROVIDER);
        LOGGER.info(
                "The structuring translator provider '{}' has loaded {} translators",
                StructuringTranslator.DEFAULT_PROVIDER,
                typescriptTranslators.size()
        );
        if (enableDebug) {
            LOGGER.info(
                    "The structuring translator provider '{}' has loaded {} translators: {}",
                    StructuringTranslator.DEFAULT_PROVIDER,
                    typescriptTranslators.size(),
                    collectTranslators(typescriptTranslators)
            );
        }
    }

    private static Map<LanguageTranslateTarget, List<Object>> collectTranslators(Map<LanguageTranslateTarget, Map<TranslateElementData<?>, StructuringTranslator<?>>> translators) {
        Map<LanguageTranslateTarget, List<Object>> result = new HashMap<>();
        translators.forEach((target, targetTranslators) -> result.put(target, Collections.singletonList(targetTranslators.keySet().stream().map(TranslateElementData::clazz).toList())));
        return result;
    }
}
