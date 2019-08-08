package cn.leyou.goods.client;

import cn.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
