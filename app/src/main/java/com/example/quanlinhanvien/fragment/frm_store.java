package com.example.quanlinhanvien.fragment;

import androidx.fragment.app.Fragment;

public class frm_store extends Fragment {
//    RecyclerView recyclerView;
//    TextView txtTotal;
//    adapter_store adapter_store;
//    Button btnsignup,btnUpdate;
//    ArrayList<cuahang>list;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frm_diemdanh, container, false);
//        txtTotal = view.findViewById(R.id.totalListNV);
//        recyclerView = view.findViewById(R.id.rcv_nhanvien);
//        btnsignup = view.findViewById(R.id.btnsignup);
//        btnUpdate = view.findViewById(R.id.btnupdate);
//        list = new ArrayList<>();
//        loaddata();
//        demoCallAPI();
//        return view;
//    }
//    private void loaddata(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter_store = new adapter_store(getContext(),list);
//        recyclerView.setAdapter(adapter_store);
//    }
//    private void demoCallAPI() {
//
//        service_API requestInterface = new Retrofit.Builder()
//                .baseUrl(Base_Service)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(service_API.class);
//
//        new CompositeDisposable().add(requestInterface.getModelCHAPI()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, this::handleError)
//        );
//    }
//
//    private void handleResponse(ArrayList<cuahang> list1) {
//        //API trả về dữ liệu thành công, thực hiện việc lấy data
//        for(int i =0; i <list1.size(); i++){
//            list.add(i,list1.get(i));
//        }
//        adapter_store.notifyDataSetChanged();
//        txtTotal.setText("Total store: "+list1.size());
//    }
//
//    private void handleError(Throwable error) {
//        Log.d("erro", error.toString());
//        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
//    }
}
