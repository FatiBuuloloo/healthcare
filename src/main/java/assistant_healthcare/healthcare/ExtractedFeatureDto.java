package assistant_healthcare.healthcare;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ExtractedFeatureDto(
                @JsonProperty("summary") @JsonPropertyDescription("2-3 sentence summary of what is visible in the image/ingredients and how it relates to the user's medical history. No advice here. Write in the response language specified by the user message.") String summary,

                @JsonProperty("recommendation") @JsonPropertyDescription("Consumption recommendation (safe, limit, or avoid) with a short reason based on medical history and health plan. Name the specific ingredients involved. Write in the response language specified by the user message.") String recommendation,

                @JsonProperty("suggest") @JsonPropertyDescription("Practical follow-up advice: alternatives, portion/frequency limits, what to check with a doctor, warning signs. Write in the response language specified by the user message.") String suggest) {
}
