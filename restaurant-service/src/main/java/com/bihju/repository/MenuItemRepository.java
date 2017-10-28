package com.bihju.repository;

import com.bihju.model.MenuItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "menuitems")
public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, String> {
    @RestResource(rel = "by-rid", description = @Description("Get all menuitems by restaurant id"))
    public List<MenuItem> findAllByRestaurantId(String rid);

    @RestResource(rel = "by-name", description = @Description("Get menuitem by item name"))
    public MenuItem findByName(@Param("name") String name);
}
