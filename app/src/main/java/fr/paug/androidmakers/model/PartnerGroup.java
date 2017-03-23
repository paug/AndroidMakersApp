package fr.paug.androidmakers.model;

import android.text.TextUtils;

import java.util.List;

/**
 * @author Adrien Vitti
 * @since 2017.03.23
 */

public class PartnerGroup {

    public enum PartnerType {Unknown, SilverSponsor, Media}
    // TODO: 23/03/2017 Google is Gold

    public final PartnerType type;
    public final List<Partners> partnersList;

    public PartnerGroup(PartnerType type, List<Partners> partnersList) {
        this.type = type;
        this.partnersList = partnersList;
    }

    static PartnerType getPartnerTypeFromString(String typeName) {
        if (!TextUtils.isEmpty(typeName)) {
            if ("media".equalsIgnoreCase(typeName)) {
                return PartnerType.Media;
            } else if ("silver sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.SilverSponsor;
            }
        }
        return PartnerType.Unknown;
    }

    public List<Partners> getPartnersList() {
        return partnersList;
    }

}