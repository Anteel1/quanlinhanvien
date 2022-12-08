package com.example.quanlinhanvien.fragment;


import androidx.fragment.app.Fragment;

public class frm_dangkylichlam extends Fragment {
//    ArrayList<chamcong> list;
//    TextView txtsonv;
//    ListView listviewcc;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frm_dangkylichlam, container, false);
//        txtsonv = view.findViewById(R.id.txtsonv);
//        listviewcc = view.findViewById(R.id.listviewcc);
//        list = new ArrayList<>();
//
//        CallAPIcc();
//
//        show(list);
//        return view;
//    }
//
//    private void CallAPIcc() {
//
//        service_API requestInterface = new Retrofit.Builder()
//                .baseUrl(Base_Service)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(service_API.class);
//
//        new CompositeDisposable().add(requestInterface.getModelAPI_chamcong()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleRespons, this::handleError)
//        );
//    }
//
//    private void handleRespons(ArrayList<chamcong> list1) {
//        //API trả về dữ liệu thành công, thực hiện việc lấy data
//        txtsonv.setText("" + list1.size());
//        for (int i = 0; i < list1.size(); i++) {
//            list.add(list1.get(i));
//        }
//
//        show(list);
//

//    }

//
//    private void handleError(Throwable error) {
//
//        Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();
//
//
//        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
//    }
//
//    private void show(ArrayList<chamcong> listcc) {
//        adapter_chamcong adapter_chamcong = new adapter_chamcong(listcc, getContext());
//        listviewcc.setAdapter(adapter_chamcong);
//
//
//    }
//

}
