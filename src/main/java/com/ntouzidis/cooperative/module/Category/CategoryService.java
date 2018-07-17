package com.ntouzidis.cooperative.module.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    List<Category> getAllSortedAndOrdered(String smb, String omb);

}
