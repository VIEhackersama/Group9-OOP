import pandas as pd
import numpy as np
from scipy.stats import gaussian_kde

# Đọc dữ liệu
df = pd.read_csv("./data/muanhadat_data_dangcap.csv")

def get_price_peak(prices):
    """Trả về giá có mật độ xác suất cao nhất trong phân phối Gaussian KDE."""
    try:
        kde = gaussian_kde(prices)
        price_range = np.linspace(prices.min(), prices.max(), 200)
        density = kde(price_range)
        return price_range[np.argmax(density)]
    except:
        return prices.mean()

# Tính 'giá trung tâm thực tế' cho từng phường
price_peaks = df.groupby("area")["price"].apply(get_price_peak).rename("peak_price")

# Gộp vào dataframe
df = df.merge(price_peaks, on="area", how="left")


df["distance_center"] = df["distance_center"] * (df["peak_price"] / df["price"])

# Xóa cột phụ
df.drop(columns=["peak_price"], inplace=True)

# Ghi đè file
df.to_csv("muanhadat_data_adjusted.csv", index=False, encoding="utf-8-sig")

print("✅")