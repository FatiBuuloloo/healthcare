package assistant_healthcare.healthcare;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/healthcare")
public class HealthCareController {
    private final HealthCareService service;

    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testService(@ModelAttribute RequestDto request) {
        String text = request.getHistoryMedical();
        MultipartFile image = request.getImage();
        return ResponseEntity.ok("Berhasil Menerima Gambar dan teks");
    }

    @PostMapping(value = "/summary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ExtractedFeatureDto getSummaryService(@ModelAttribute RequestDto request) {
        return service.getSummary(request);
    }

}