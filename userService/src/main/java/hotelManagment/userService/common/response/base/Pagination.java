package hotelManagment.userService.common.response.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pagination implements Serializable {

    private Integer currentPage;
    private Integer nextPage;
    private Integer previousPage;
    private Integer totalPages;
    private Integer perPage;
    private Integer totalEntites;

}
