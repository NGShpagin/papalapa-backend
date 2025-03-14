package version_1.providers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import version_1.dto.WBResponseDtos.WBGoodsResponseDto;
import version_1.dto.WBResponseDtos.WBReviewResponseDto;

@Slf4j
@Service
public class WBProvider {

    private final WebClient webClient;

    public WBProvider() {
        webClient = WebClient.builder().build();
    }

    public WBReviewResponseDto getReviewList(boolean isAnswered, int reviewQuantity)
            throws NullPointerException, HttpClientErrorException, IllegalArgumentException {

        try {
            String authToken = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjQxMjE3djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTc1MDY0NzE3NSwiaWQiOiIwMTkzZWVkYi02MjJmLTcwOGUtYTJlNS1iOGM2NTQ0NTgwMWYiLCJpaWQiOjY3MTk5ODM0LCJvaWQiOjEzNTQ1MjgsInMiOjEwNzM3NDE5NTIsInNpZCI6IjU1NDFmMzdiLWQ4NmYtNDY2My04ZmFmLTlkNjIwOGJjZGQzYSIsInQiOmZhbHNlLCJ1aWQiOjY3MTk5ODM0fQ.LT2VfnQKCBJ-L4JzdfYLxFSTW_pFKg_Bih-Wd1-QdogcYhtsE_yCYtG_R3B8eVmzUyYXwBspZWbE3MXbDBfmTQ";
            return webClient
                    .get()
                    .uri("https://feedbacks-api.wildberries.ru/api/v1/feedbacks?" +
                            "isAnswered=" + isAnswered +
                            "&take=" + reviewQuantity +
                            "&skip=" + 0)
                    .header("Authorization", authToken)
                    .retrieve()
                    .bodyToMono(WBReviewResponseDto.class)
//                    .bodyToFlux((WBReviewResponseDto.class))
//                    .collectList()
                    .block();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.TooManyRequests e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        } catch (HttpClientErrorException.BadRequest e) {
            throw new RuntimeException(e);
        }
    }

    public WBGoodsResponseDto getItemWithPriceByNmId(int filterNmId) {
        String authToken = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjUwMjE3djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTc1NzU3NDYzNSwiaWQiOiIwMTk1OGJjNC0wODMyLTczMTItYWUxMS0xOTk2ODc4MmJlNzQiLCJpaWQiOjY3MTk5ODM0LCJvaWQiOjEzNTQ1MjgsInMiOjEwNzM3NDE4MzIsInNpZCI6IjU1NDFmMzdiLWQ4NmYtNDY2My04ZmFmLTlkNjIwOGJjZGQzYSIsInQiOmZhbHNlLCJ1aWQiOjY3MTk5ODM0fQ.DW6xMngXfcUV7oUpK5Jkoc6yJgA4c6FSTuyTi4F5R7zUPJVSKykgZTL3z4Ejlg0i9ouWkYlkLGRhsdxoQst-Fw";
        try {
            return webClient
                    .get()
                    .uri("https://discounts-prices-api.wildberries.ru/api/v2/list/goods/filter?" +
                            "limit=10&filterNmId=" + filterNmId)
                    .header("Authorization", authToken)
                    .retrieve()
                    .bodyToMono(WBGoodsResponseDto.class)
                    .block();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.TooManyRequests e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
