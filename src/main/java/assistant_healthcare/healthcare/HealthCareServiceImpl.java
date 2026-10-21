package assistant_healthcare.healthcare;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import assistant_healthcare.healthcare.Exception.ImageException;
import assistant_healthcare.healthcare.Exception.TextException;

@Service
public class HealthCareServiceImpl implements HealthCareService {
    private final ChatClient client;
    private final String prompt;
    private static final Set<String> TYPE_ALLOWED = Set.of("image/jpeg", "image/jpg", "image/png", "image/webp");
    // private static final long MAX_SIZE_IMAGE = 5L * 1024 * 1024;

    public HealthCareServiceImpl(ChatClient.Builder builder, ResourceLoader loader) throws IOException {
        this.prompt = loadResourceAsString(loader, "classpath:assistant_healthcare/healthcare/prompt.txt");
        this.client = builder.defaultSystem(prompt).build();
    }

    private String loadResourceAsString(ResourceLoader loader, String location) throws IOException {
        Resource resource = loader.getResource(location);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Override
    public ExtractedFeatureDto getSummary(RequestDto request) {
        validateImage(request.getImage());

        boolean hasHistory = request.getHistoryMedical() != null && !request.getHistoryMedical().isEmpty();
        boolean hasQuestion = request.getQuestion() != null && !request.getQuestion().isEmpty();
        if (!hasHistory && !hasQuestion) {
            throw new TextException("No Medical History and No Question", "Atleast Medical History Must Filled");
        }
        if (!hasHistory) {
            throw new TextException("No Medical History", "Must Input Medical History");
        }

        Resource res = request.getImage().getResource();
        MimeType mime = MimeTypeUtils.parseMimeType(request.getImage().getContentType());
        String medicalHistory = request.getHistoryMedical();
        String question = request.getQuestion();

        ExtractedFeatureDto response = client
                .prompt()
                .user(u -> u.text("Medical History: " + medicalHistory + "\nQuestion: " + question)
                        .media(mime, res))
                .call()
                .entity(ExtractedFeatureDto.class);
        return response;
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ImageException("Image Not Found", "Must input the image");
        }
        if (!TYPE_ALLOWED.contains(image.getContentType())) {
            throw new ImageException("Type does not support", "Type Must be JPG, JPEG, PNG, or WEBP");
        }
    }
}
