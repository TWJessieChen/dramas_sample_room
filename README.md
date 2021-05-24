# dramas_sample_room
使用android room 重新設計架構

This is Android Dramas Sample code(MVVM架構設計).

## 架構設計上的概論
1.MVVM，ViewModel 搭配 Coroutine 架構  
2.使用android room 儲存資料，目的解決無網路下，還能正常顯示資料  
3.使用SharedPreferences，用於在暫存前一次所使用app的狀態資訊(search word)  
4.使用gildory作者開源的sdk，目的讓okhttp可以跟Coroutine搭配使用，形成blocking狀態  
5.使用android room 搭配 kotlinx-coroutines 的 flow 觀察room 有變化的話，可以異步同步到 viewmodel 轉換 liveData  
6.android room SQL 語法使用 LIKE 模糊查詢方法  
7.一定要用 kapt 'androidx.room:room-compiler:2.2.5',要不然執行過程中會有問題  


## 架構設計上的缺點  
1.在Filter word上，需要了解SQLite 語法下query 等等  
2.Refersh adapter 上會有閃動的使用者體驗  


## Implementation sdk lists
1.kotlinx-coroutines  
2.lifecycle-viewmodel  
3.lifecycle-livedata  
4.squareup.okhttp3:okhttp  
5.github.bumptech.glide:glide  
6.androidx.room:room-ktx  
7.google.code.gson  
8.ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0  


Happy Fun, Enjoy the Android Dramas Sample code.
