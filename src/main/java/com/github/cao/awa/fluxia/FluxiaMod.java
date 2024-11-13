package com.github.cao.awa.fluxia;

import com.github.cao.awa.language.translator.builtin.typescript.translate.kts.TypescriptKotlinScriptTranslator;
import com.github.cao.awa.language.translator.translate.LanguageTranslator;
import com.github.cao.awa.language.translator.translate.lang.TranslateTarget;
import com.github.cao.awa.language.translator.translate.lang.element.TranslateElementData;
import com.github.cao.awa.sinuatum.util.collection.CollectionFactor;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class FluxiaMod implements ModInitializer {
    private final static Logger LOGGER = LogManager.getLogger("LanguageTranslator");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading language translator {} provider '{}' for [typescript]", LanguageTranslator.getVersion(), LanguageTranslator.DEFAULT_PROVIDER);
        TypescriptKotlinScriptTranslator.postRegister();

        Map<TranslateTarget, Map<TranslateElementData<?>, LanguageTranslator<?>>> typescriptTranslators = LanguageTranslator.getTranslators(LanguageTranslator.DEFAULT_PROVIDER);
        LOGGER.info(
                "The language translator provider '{}' has loaded {} translators: {}",
                LanguageTranslator.DEFAULT_PROVIDER,
                typescriptTranslators.size(),
                collectTranslators(typescriptTranslators)
        );
    }

    private static Map<TranslateTarget, List<Object>> collectTranslators(Map<TranslateTarget, Map<TranslateElementData<?>, LanguageTranslator<?>>> translators) {
        Map<TranslateTarget, List<Object>> result = CollectionFactor.hashMap();
        translators.forEach((target, targetTranslators) -> result.put(target, Collections.singletonList(targetTranslators.keySet().stream().map(TranslateElementData::clazz).toList())));
        return result;
    }
}
