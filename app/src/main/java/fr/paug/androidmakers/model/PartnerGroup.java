package fr.paug.androidmakers.model;

import android.text.TextUtils;

import java.util.List;

/**
 * @author Adrien Vitti
 * @since 2017.03.23
 */
public class PartnerGroup {

    // TODO: 24/03/2017 main organizers ?
    public enum PartnerType {Unknown, GoldSponsor, SilverSponsor, Media, Location}

    private final PartnerType type;
    private final List<Partners> partnersList;

    public PartnerGroup(PartnerType type, List<Partners> partnersList) {
        this.type = type;
        this.partnersList = partnersList;
    }

    static PartnerType getPartnerTypeFromString(String typeName) {
        if (!TextUtils.isEmpty(typeName)) {
            if ("gold+ sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.GoldSponsor;
            } else if ("silver sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.SilverSponsor;
            } else if ("media".equalsIgnoreCase(typeName)) {
                return PartnerType.Media;
            } else if ("location".equalsIgnoreCase(typeName)) {
                return PartnerType.Location;
            }
        }
        return PartnerType.Unknown;
    }

    public List<Partners> getPartnersList() {
        return partnersList;
    }

}