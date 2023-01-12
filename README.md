# Nöbetçi Eczane Android Uygulaması

Bu uygulama ile kullanıcılar Türkiye'nin 81 ilindeki ve KKTC'deki nöbetçi eczanelere kolaylıkla ulaşabilmektedir. Kullanıcılar kendi konumlarına yakın olan eczaneleri listeyeleyebilir veya harita üzerinde gösterebilir. Konumunu paylaşmak istemeyen veya dilediği konuma bakmak isteyen kullanıcılar da listeden il ve ilçe seçimi yaparak istedikleri bölgedeki nöbetçi eczanelere ulaşabilir.

## Ana Ekran

Uygulamadaki tüm string değerler strings dosyasından alındığı için, uygulamanın çoklu dil desteği vardır. Uygulamanın renkleri de yine dosyadan okunduğu için karanlık mod desteği de vardır. Kullanıcılar ana ekranda nöbetçi eczane aramak istedikleri il ve ilçeyi seçip arama yapabilirler. Bunun yanı sıra konum izni vererek yakınlarındaki nöbetçi eczaneleri de görebilirler.

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/main_light.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/main_light.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/EN/main_dark.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/EN/main_dark.png" width="200" style="max-width:100%;"></a>


## Listeleme Ekranları

Ana ekranda arama veya yakındakileri gösterme butonlarına basıldığında hata yoksa uygulama, listeleme ekranına geçer. Burada eğer sadece ile seçimi yapıldıysa nöbetçi eczaneler ilçe sırasına göre sıralanır. Kullanıcı yakındaki eczaneleri görmek istiyorsa ek olarak mesafe bilgisi yer alır ve en altta yakındaki eczaneleri harita üzerinde gösterecek ekrana ait buton bulunur.

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_county.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_county.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_county_dark.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_county_dark.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_distance.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/list_distance.png" width="200" style="max-width:100%;"></a>

## Detay ve Harita Sayfaları

Liste ekranından bir eczane seçildiğinde o eczaneye ait detay ekranı açılır. Bu ekranda eczaneye dair temel bilgilerin yanı sıra konum bilgisi de bulunur. Kullanıcılar isterse bu konuma navigasyon başlatabilir veya Google Haritalar üzerinde daha detaylı görüntüleyebilir. 

Diğer ekran ise yakınlık listesi ekranındaki haritada göster butonunun yönlendirdiği ekrandır. Burada kullanıcı, yakınındaki eczaneleri harita üzerinde görüntüleyebilmektedir. Deneme konumu olarak Kadıköy Sahil seçilmiştir.

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/details.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/details.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/map_nearby.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/map_nearby.png" width="200" style="max-width:100%;"></a>

## İzin İsteme ve Uyarılar

Uygulama, kullanıcıdan 2 tane tehlikeli izin istemektedir bunlar telefon araması ve konuma erişim izinleridir. Kullanıcılar konumlarına göre eczane aramak istiyorlarsa konum izni vermeleri gerekmektedir. Detay sayfasında yer alan telefon numarasına basınca da kullanıcılar direkt olarak eczaneyi arayabilirler ancak bunun için de izin vermeleri gerekmektedir. Eğer kullanıcı bu izinleri vermek istemiyorsa uygulamayı yine hatasız, çökmesiz ve sorunsuz olarak kullanabilmektedir.

Kullanıcılar 2 kez "izin verme" butonuna basarlarsa Android sistemi bunu "daha fazla gösterme" olarak algıladığı için izin ekranı çıkartmayı bırakıyor. Bu durumda kullanıcının işini kolaylaştırmak amacıyla tüm izinler için Snackbar ile ayarlara yönlendirme eklenmiştir.

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/permission.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/permission.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/permission_denied.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Nobetci-Eczane/blob/main/screenshots/TR/permission_denied.png" width="200" style="max-width:100%;"></a>


## Kullanılar Teknolojiler ve Yapılar

- Kotlin & Android
- MVVM
- Retrofit
- Google Maps
- Location
- Coroutines
- Dagger & Hilt
- Data Binding
- View Binding
- Fragments
- Dangerous Permissions
