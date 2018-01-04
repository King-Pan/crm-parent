package club.javalearn.crm.utils;

import lombok.Data;

import java.util.List;


/**
 * crm-parent
 *
 * @author king-pan
 * @date 2017-12-26
 **/
@Data
public class BootstrapMessage<T> implements Message<T> {
    private List<T> rows;
    private Integer start;
    private Integer limit;
    private Long total;
}
