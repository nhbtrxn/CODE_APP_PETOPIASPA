package com.nhom9.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nhom9.adapters.ProductAdapter;
import com.nhom9.adapters.ProductSliderAdapter;
import com.nhom9.api.ApiClient;
import com.nhom9.api.ApiService;
import com.nhom9.models.Product;
import com.nhom9.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    RecyclerView rvDogFood, rvDogMedicine, rvDogSanitary,rvDogAccessory,rvDogClothes,rvDogTool;
    RecyclerView rvCatFood, rvCatMedicine, rvCatSanitary,rvCatAccessory,rvCatClothes,rvCatTool,rvCatCabinet, rvCatSand;
    ProductAdapter dogFoodAdapter,dogMedicineAdapter,dogSanitaryAdapter, dogAccesoryAdapter,dogClothesAdapter, dogToolAdapter;
    ProductAdapter catFoodAdapter, catMedicineAdapter, catSanitaryAdapter, catAccessoryAdapter, catClothesAdapter, catToolAdapter, catCabinetAdapter, catSandAdapter;

    List<Product> dogFood,dogMedicine,dogSanitary,dogAccesory,dogClothes, dogTool,allProducts;
    List<Product> catFood, catMedicine, catSanitary, catAccessory, catClothes, catTool, catCabinet, catSand;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        viewPager = view.findViewById(R.id.productPager);

        List<Integer> layouts = Arrays.asList(
                R.layout.item_product_dog,
                R.layout.item_product_cat
        );

        ProductSliderAdapter adapter = new ProductSliderAdapter(requireContext(), layouts);
        viewPager.setAdapter(adapter);

        initRecyclerViews(view);
        initAdapters();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProductFromApi();
    }

    private void getProductFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProducts = response.body();
                    loadAllProducts();
                } else {
                    Toast.makeText(requireContext(), "Không thể tải danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllProducts() {
        loadCategory("c_ta_", dogFood, dogFoodAdapter, "Dog Food");
        loadCategory("c_th_", dogMedicine, dogMedicineAdapter, "Dog Medicine");
        loadCategory("c_dc_", dogSanitary, dogSanitaryAdapter, "Dog Sanitary");
        loadCategory("c_pk_", dogAccesory, dogAccesoryAdapter, "Dog Accessory");
        loadCategory("c_qa_", dogClothes, dogClothesAdapter, "Dog Clothes");
        loadCategory("c_dd_", dogTool, dogToolAdapter, "Dog Tool");

        loadCategory("m_ta_", catFood, catFoodAdapter, "Cat Food");
        loadCategory("m_th_", catMedicine, catMedicineAdapter, "Cat Medicine");
        loadCategory("m_dc_", catSanitary, catSanitaryAdapter, "Cat Sanitary");
        loadCategory("m_pk_", catAccessory, catAccessoryAdapter, "Cat Accessory");
        loadCategory("m_qa_", catClothes, catClothesAdapter, "Cat Clothes");
        loadCategory("m_dd_", catTool, catToolAdapter, "Cat Tool");
        loadCategory("m_ls_", catCabinet, catCabinetAdapter, "Cat Cabinet");
        loadCategory("m_ca_", catSand, catSandAdapter, "Cat Sand");

    }

    private void loadCategory(String prefix, List<Product> targetList, ProductAdapter adapter, String typeLabel) {
        targetList.clear();
        Set<String> seenIds = new HashSet<>();
        List<String> productIds = new ArrayList<>();

        for (Product product : allProducts) {
            String id = product.getProductId();
            if (id != null && id.startsWith(prefix) && !seenIds.contains(id)) {
                seenIds.add(id);
                productIds.add(id);
            }
        }

        final int[] completed = {0};
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        for (String productId : productIds) {
            Call<Product> call = apiService.getProductById(productId);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                    completed[0]++;
                    if (response.isSuccessful() && response.body() != null) {
                        targetList.add(response.body());
                    }

                    if (completed[0] == productIds.size()) {
                        adapter.notifyDataSetChanged();
                        if (targetList.isEmpty()) {
                            Toast.makeText(requireContext(), "Không có sản phẩm " + typeLabel, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                    completed[0]++;
                    Log.e("ProductDebug", "Lỗi kết nối API " + typeLabel + ": " + t.getMessage());
                    if (completed[0] == productIds.size()) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void initAdapters() {
        dogFood = new ArrayList<>();
        dogMedicine = new ArrayList<>();
        dogSanitary = new ArrayList<>();
        dogAccesory = new ArrayList<>();
        dogClothes = new ArrayList<>();
        dogTool = new ArrayList<>();

        catFood = new ArrayList<>();
        catMedicine = new ArrayList<>();
        catSanitary = new ArrayList<>();
        catAccessory = new ArrayList<>();
        catClothes = new ArrayList<>();
        catTool = new ArrayList<>();
        catCabinet = new ArrayList<>();
        catSand = new ArrayList<>();


        dogFoodAdapter = new ProductAdapter(requireContext(),dogFood);
        dogMedicineAdapter = new ProductAdapter(requireContext(),dogMedicine);
        dogSanitaryAdapter = new ProductAdapter(requireContext(),dogSanitary);
        dogAccesoryAdapter = new ProductAdapter(requireContext(), dogAccesory);
        dogClothesAdapter = new ProductAdapter(requireContext(),dogClothes);
        dogToolAdapter = new ProductAdapter(requireContext(),dogTool);

        catFoodAdapter = new ProductAdapter(requireContext(), catFood);
        catMedicineAdapter = new ProductAdapter(requireContext(), catMedicine);
        catSanitaryAdapter = new ProductAdapter(requireContext(), catSanitary);
        catAccessoryAdapter = new ProductAdapter(requireContext(), catAccessory);
        catClothesAdapter = new ProductAdapter(requireContext(), catClothes);
        catToolAdapter = new ProductAdapter(requireContext(), catTool);
        catCabinetAdapter = new ProductAdapter(requireContext(), catCabinet);
        catSandAdapter = new ProductAdapter(requireContext(), catSand);


        rvDogAccessory.setAdapter(dogAccesoryAdapter);
        rvDogFood.setAdapter(dogFoodAdapter);
        rvDogMedicine.setAdapter(dogMedicineAdapter);
        rvDogSanitary.setAdapter(dogSanitaryAdapter);
        rvDogClothes.setAdapter(dogClothesAdapter);
        rvDogTool.setAdapter(dogToolAdapter);

        rvCatFood.setAdapter(catFoodAdapter);
        rvCatMedicine.setAdapter(catMedicineAdapter);
        rvCatSanitary.setAdapter(catSanitaryAdapter);
        rvCatAccessory.setAdapter(catAccessoryAdapter);
        rvCatClothes.setAdapter(catClothesAdapter);
        rvCatTool.setAdapter(catToolAdapter);
        rvCatCabinet.setAdapter(catCabinetAdapter);
        rvCatSand.setAdapter(catSandAdapter);
    }

    private void initRecyclerViews(View view) {
        rvDogFood = view.findViewById(R.id.rvDogFood);
        rvDogClothes= view.findViewById(R.id.rvDogClothes);
        rvDogSanitary = view.findViewById(R.id.rvDogSanitary);
        rvDogMedicine = view.findViewById(R.id.rvDogMedicine);
        rvDogAccessory = view.findViewById(R.id.rvDogAccessory);
        rvDogTool = view.findViewById(R.id.rvDogTool);

        rvCatFood = view.findViewById(R.id.rvCatFood);
        rvCatMedicine = view.findViewById(R.id.rvCatMedicine);
        rvCatSanitary = view.findViewById(R.id.rvCatSanitary);
        rvCatAccessory = view.findViewById(R.id.rvCatAccessory);
        rvCatClothes = view.findViewById(R.id.rvCatClothes);
        rvCatTool = view.findViewById(R.id.rvCatTool);
        rvCatCabinet = view.findViewById(R.id.rvCatCabinet);
        rvCatSand = view.findViewById(R.id.rvCatSand);



       rvDogFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       rvDogAccessory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       rvDogClothes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       rvDogSanitary.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       rvDogTool.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       rvDogMedicine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvCatFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatMedicine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatSanitary.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatAccessory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatClothes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatTool.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatCabinet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCatSand.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}