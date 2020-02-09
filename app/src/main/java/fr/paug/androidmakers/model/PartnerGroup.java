package fr.paug.androidmakers.model;

import androidx.annotation.StringRes;
import android.text.TextUtils;

import java.util.List;

import fr.paug.androidmakers.R;

//TODO get rid of this, handle localization in Firebase
public class PartnerGroup {

    public enum PartnerType {
        Unknown, GoldSponsor, SilverSponsor, OtherSponsor, VirtualSponsor,
        SpeakerSponsor, Makers, Media, Location, SpecialThanks;

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
                case Makers:
                    return R.string.makers_sponsor;
                case Media:
                    return R.string.media_sponsor;
                case Location:
                    return R.string.location_sponsor;
                case SpecialThanks:
                    return R.string.specialthanks_sponsor;
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
                case SpecialThanks:
                    return 2;
                case Makers:
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
            switch (typeName.toLowerCase()) {
                case "gold sponsor":
                    return PartnerType.GoldSponsor;
                case "silver sponsor":
                    return PartnerType.SilverSponsor;
                case "other sponsor":
                    return PartnerType.OtherSponsor;
                case "virtual sponsor":
                    return PartnerType.VirtualSponsor;
                case "individual speaker sponsor":
                    return PartnerType.SpeakerSponsor;
                case "makers":
                    return PartnerType.Makers;
                case "media":
                    return PartnerType.Media;
                case "location":
                    return PartnerType.Location;
                case "special thanks":
                    return PartnerType.SpecialThanks;
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