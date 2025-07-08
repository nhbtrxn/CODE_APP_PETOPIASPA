package com.nhom9.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhom9.adapters.ServiceAdapter;
import com.nhom9.adapters.SliderAdapter;
import com.nhom9.api.ApiClient;
import com.nhom9.api.ApiService;
import com.nhom9.models.Service;
import com.nhom9.myapplication.R;
import com.nhom9.myapplication.databinding.FragmentServiceBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends Fragment {
    RecyclerView rvHotelServices, rvSpaServices, rvHealthServices,rvOtherServices;
    ServiceAdapter hotelAdapter,spaAdapter,healthAdapter, otherAdapter;
    List<Service> hotelServices,spaServices,healthServices,otherServices,allServices;
    FragmentServiceBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
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
        binding = FragmentServiceBinding.inflate(inflater, container, false);

        initRecyclerViews(binding.getRoot());
        initAdapters();
        setBannerSlider();

        return binding.getRoot();
    }


    private void setBannerSlider() {
        int[] bannerImages = new int[] {
                R.drawable.ar_banner,
                R.drawable.service_banner,
        };

        SliderAdapter sliderAdapter = new SliderAdapter(requireContext(), bannerImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvBanner.setLayoutManager(layoutManager);
        binding.rvBanner.setAdapter(sliderAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getServicesFromApi();
    }
    private void loadAllServices() {
        loadCategory(Collections.singletonList("dv_ks"), hotelServices, hotelAdapter, "Hotel");
        loadCategory(Arrays.asList("dv_tamcao", "dv_tamcat","dv_tamvs","dv_goroilong","dv_taylong","dv_nhuomlong"), spaServices, spaAdapter, "Spa");
        loadCategory( Arrays.asList("dv_trinam","dv_trive","dv_bangbo"), healthServices, healthAdapter, "Health");
        loadCategory(Arrays.asList("dv_caovoi", "dv_catdua", "dv_vstai", "dv_cattuyenhoi"), otherServices, otherAdapter, "Other");
    }
    private void loadCategory(List<String> prefixes, List<Service> targetList, ServiceAdapter adapter, String typeLabel) {
        targetList.clear();
        Set<String> seenIds = new HashSet<>();
        List<String> serviceIds = new ArrayList<>();

        for (Service service : allServices) {
            String id = service.getServiceId();
            if (id != null && !seenIds.contains(id)) {
                for (String prefix : prefixes) {
                    if (id.startsWith(prefix)) {
                        seenIds.add(id);
                        serviceIds.add(id);
                        break;
                    }
                }
            }
        }

        final int[] completed = {0};
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        for (String serviceId : serviceIds) {
            Call<Service> call = apiService.getServiceById(serviceId);
            call.enqueue(new Callback<Service>() {
                @Override
                public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                    completed[0]++;
                    if (response.isSuccessful() && response.body() != null) {
                        targetList.add(response.body());
                    }
                    if (completed[0] == serviceIds.size()) {
                        adapter.notifyDataSetChanged();
                        if (targetList.isEmpty()) {
                            Toast.makeText(requireContext(), "Không có dịch vụ " + typeLabel, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Service> call, @NonNull Throwable t) {
                    completed[0]++;
                    Log.e("ServiceDebug", "Lỗi kết nối API " + typeLabel + ": " + t.getMessage());
                    if (completed[0] == serviceIds.size()) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void initRecyclerViews(View view) {

        rvHotelServices = view.findViewById(R.id.rvHotelServices);
        rvSpaServices = view.findViewById(R.id.rvSpaServices);
        rvHealthServices = view.findViewById(R.id.rvHealthServices);
        rvOtherServices = view.findViewById(R.id.rvOtherServices);

        rvHotelServices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSpaServices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHealthServices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvOtherServices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void initAdapters() {

        hotelServices = new ArrayList<>();
        spaServices = new ArrayList<>();
        healthServices = new ArrayList<>();
        otherServices = new ArrayList<>();

        hotelAdapter = new ServiceAdapter(requireContext(), hotelServices);
        spaAdapter = new ServiceAdapter(requireContext(), spaServices);
        healthAdapter = new ServiceAdapter(requireContext(), healthServices);
        otherAdapter = new ServiceAdapter(requireContext(), otherServices);

        rvHotelServices.setAdapter(hotelAdapter);
        rvSpaServices.setAdapter(spaAdapter);
        rvHealthServices.setAdapter(healthAdapter);
        rvOtherServices.setAdapter(otherAdapter);
    }

    private void getServicesFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Service>> call = apiService.getService();

        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(@NonNull Call<List<Service>> call, @NonNull Response<List<Service>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allServices = response.body();
                    loadAllServices();
                } else {
                    Toast.makeText(requireContext(), "Không thể tải danh sách dịch vụ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Service>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}