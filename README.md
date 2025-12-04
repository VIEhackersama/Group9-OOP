# Group9-OOP — Hanoi Housing Price Prediction

Dự án xây dựng ứng dụng dự đoán giá nhà tại Hà Nội. Hệ thống gồm pipeline thu thập & xử lý dữ liệu, huấn luyện mô hình Machine Learning và một ứng dụng Java (OOP) có giao diện để người dùng nhập thông tin căn nhà và nhận giá dự đoán.

## Tính năng chính
- Crawl dữ liệu nhà đất và lưu thành dataset.
- Tiền xử lý dữ liệu (làm sạch, chuẩn hoá, hiệu chỉnh theo khu vực).
- Huấn luyện mô hình dự đoán giá nhà (regression).
- Ứng dụng desktop Java với GUI:
  - Đăng ký / đăng nhập.
  - Nhập thông tin căn nhà.
  - Hiển thị giá dự đoán.

## Công nghệ sử dụng
- **Java (OOP) + Maven**: xây dựng ứng dụng desktop và kiến trúc chương trình.
- **Java Swing/AWT**: tạo giao diện người dùng (GUI).
- **Python**: xử lý dữ liệu và train model.
- **Scrapy**: crawl dữ liệu nhà đất.
- **XGBoost (XGBRegressor)** *(có thử nghiệm thêm CatBoost)*: mô hình dự đoán giá.

## Ghi chú
Dataset và model đã được lưu trong repo để ứng dụng Java có thể gọi và dự đoán trực tiếp.
