package fr.paug.androidmakers.model;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import java.util.List;

import fr.paug.androidmakers.R;

/**
 * @author Adrien Vitti
 * @since 2017.03.23
 */
public class PartnerGroup {

    public enum PartnerType {
        Unknown, GoldSponsor, SilverSponsor, OtherSponsor, Media, Location;

        @StringRes
        public int getName() {
            switch (this) {
                case GoldSponsor:
                    return R.string.gold_sponsor;
                case SilverSponsor:
                    return R.string.silver_sponsor;
                case OtherSponsor:
                    return R.string.other_sponsor;
                case Media:
                    return R.string.media_sponsor;
                case Location:
                    return R.string.location_sponsor;
                default:
                    return 0;
            }
        }
    }

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
            } else if ("other sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.OtherSponsor;
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

    public PartnerType getPartnerType() {
        return type;
    }

}