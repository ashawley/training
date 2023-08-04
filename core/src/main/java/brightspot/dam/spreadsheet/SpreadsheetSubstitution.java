package brightspot.dam.spreadsheet;

import java.util.Optional;

import brightspot.attachment.AttachmentType;
import brightspot.google.drive.GoogleDriveImport;
import brightspot.image.WebImageAsset;
import brightspot.microsoft.drives.MicrosoftDrivesImport;
import brightspot.promo.attachment.AttachmentPromotableWithOverrides;
import com.psddev.cms.db.Site;
import com.psddev.dari.util.StorageItem;
import com.psddev.dari.util.Substitution;
import org.apache.commons.io.FileUtils;

public class SpreadsheetSubstitution extends Spreadsheet implements
    Substitution,
    AttachmentPromotableWithOverrides,
    GoogleDriveImport,
    MicrosoftDrivesImport {

    // --- AttachmentPromotableWithOverrides support ---

    @Override
    public String getAttachmentPromotableTitleFallback() {
        return getAttachmentTitle();
    }

    @Override
    public String getAttachmentPromotableDescriptionFallback() {
        return getDescription();
    }

    @Override
    public WebImageAsset getAttachmentPromotableImageFallback() {
        return null;
    }

    @Override
    public String getAttachmentPromotableFileSizeFallback() {
        return Optional.ofNullable(getFile())
            .map(StorageItem::getHttpHeaders)
            .map(headersMap -> headersMap.get("Content-Length"))
            .map(sizes -> sizes.get(0))
            .map(size -> FileUtils.byteCountToDisplaySize(Long.parseLong(size)))
            .orElse(null);
    }

    @Override
    public String getAttachmentPromotableDownloadUrl(Site site) {
        return getAttachmentFileUrl();
    }

    @Override
    public String getAttachmentPromotableFileName() {
        return getAttachmentFilename();
    }

    @Override
    public String getAttachmentPromotableIconType() {
        return Optional.ofNullable(getAttachmentFileMimeType())
            .map(AttachmentType::getByMimeType)
            .map(AttachmentType::getIconType)
            .orElse(null);
    }

    // --- GoogleDriveImport support ---

    @Override
    public String getDefaultGoogleDriveExtension() {
        return ".xlsx";
    }

    @Override
    public void setFileFromGoogleDrive(StorageItem file) {
        setFile(file);
    }

    @Override
    public void setTitleFromGoogleDrive(String title) {
        setTitle(title);
    }

    @Override
    public boolean supportsGoogleDriveImport(String mimeType) {
        return Optional.ofNullable(getState().getType().getField("file"))
            .filter(field -> field.getMimeTypes() != null && field.getMimeTypes().contains(mimeType))
            .isPresent();
    }

    // --- MicrosoftDriveImport support ---

    @Override
    public String getDefaultMicrosoftDriveExtension() {
        return ".xlsx";
    }

    @Override
    public void setFileFromMicrosoftDrive(StorageItem file) {
        setFile(file);
    }

    @Override
    public void setTitleFromMicrosoftDrive(String title) {
        setTitle(title);
    }

    @Override
    public boolean supportsMicrosoftDriveImport(String mimeType) {
        return Optional.ofNullable(getState().getType().getField("file"))
            .filter(field -> field.getMimeTypes() != null && field.getMimeTypes().contains(mimeType))
            .isPresent();
    }
}
