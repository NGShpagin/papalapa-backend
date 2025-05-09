package version_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDTO<T> {
    private List<T> data;
    private Integer page;
    private Integer limit;
    private Integer total;
    private Integer pages;
}
