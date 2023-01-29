package gg.bayes.challenge.rest.enums;

public enum PrefixNamesEnum {

    PREFIX_HERO_NAME("npc_dota_hero_"),
    PREFIX_DARK_NAME("npc_dota_dark_"),
    PREFIX_NEUTRAL_HERO_NAME("npc_dota_neutral_"),
    PREFIX_OBSERVER_HERO_NAME("npc_dota_observer_"),
    PREFIX_GOODGUYS_HERO_NAME("npc_dota_goodguys_"),
    PREFIX_BADGUYS_HERO_NAME("npc_dota_badguys_"),
    PREFIX_ITEM_NAME("item_");

    private final String prefixName;

    PrefixNamesEnum(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getPrefixName() {
        return prefixName;
    }
}
