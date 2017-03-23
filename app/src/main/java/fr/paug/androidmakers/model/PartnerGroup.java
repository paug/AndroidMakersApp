package fr.paug.androidmakers.model;

import android.text.TextUtils;

import java.util.List;

/**
 * The class description here.
 *
 * @author Adrien Vitti
 * @since 2017.03.23
 */

public class PartnerGroup {
    enum PartnerType {Unknown, SilverSponsor, Media}

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
}
