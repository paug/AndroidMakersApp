package fr.paug.androidmakers.model;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import java.util.List;

import fr.paug.androidmakers.R;

public class PartnerGroup {

    public enum PartnerType {
        Unknown, GoldSponsor, SilverSponsor, OtherSponsor, VirtualSponsor, SpeakerSponsor, Media, Location;

        @StringRes
        public int getName() {
            switch (this) {
                case GoldSponsor:
                    return R.string.gold_sponsor;
                case SilverSponsor:
                    return R.string.silver_sponsor;
                case OtherSponsor:
                    return R.string.other_sponsor;
                case VirtualSponsor:
                    return R.string.virtual_sponsor;
                case SpeakerSponsor:
                    return R.string.speaker_sponsor;
                case Media:
                    return R.string.media_sponsor;
                case Location:
                    return R.string.location_sponsor;
                default:
                    return 0;
            }
        }

        public int getPartnerLogoSizePriority() {
            switch (this) {
                case GoldSponsor:
                    return 1;
                case SilverSponsor:
                case OtherSponsor:
                case VirtualSponsor:
                case SpeakerSponsor:
                case Location:
                    return 2;
                case Media:
                    return 3;
                default:
                    return 0;
            }
        }
    }

    private final PartnerType type;
    private final List<Partners> partnersList;

    PartnerGroup(PartnerType type, List<Partners> partnersList) {
        this.type = type;
        this.partnersList = partnersList;
    }

    static PartnerType getPartnerTypeFromString(String typeName) {
        if (!TextUtils.isEmpty(typeName)) {
            if ("gold sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.GoldSponsor;
            } else if ("silver sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.SilverSponsor;
            } else if ("other sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.OtherSponsor;
            } else if ("virtual sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.VirtualSponsor;
            }  else if ("individual speaker sponsor".equalsIgnoreCase(typeName)) {
                return PartnerType.SpeakerSponsor;
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