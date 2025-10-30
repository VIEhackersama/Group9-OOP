import pandas as pd
import numpy as np
from geopy.geocoders import Nominatim
from geopy.distance import geodesic
import time

file_path = "./data/muanhadat_data_dangcap.csv"
df = pd.read_csv(file_path)


df['room_density'] = df['bedroom_number'] / df['area']
df['bath_per_bed'] = df['bathroom_number'] / (df['bedroom_number'] + 1)
df['wide_ratio'] = df['width'] / df['height']


geolocator = Nominatim(user_agent="geoapi")
center_hn = (21.028799, 105.852349)

def distance_to_center(addr):
    try:
        if pd.isna(addr):
            return np.nan
        loc = geolocator.geocode(addr + ", Hanoi, Vietnam")
        if loc:
            return geodesic(center_hn, (loc.latitude, loc.longitude)).km
        else:
            return np.nan
    except:
        return np.nan


distances = []
for i, addr in enumerate(df['address']):
    dist = distance_to_center(addr)
    distances.append(dist)
    print(f"{i+1}/{len(df)} - {addr} → {dist}")
    time.sleep(1)  # tránh bị chặn API

df['distance_center'] = distances

# === Điền giá trị thiếu bằng trung bình ===
df['distance_center'].fillna(df['distance_center'].mean(), inplace=True)



df.to_csv(file_path, index=False, encoding='utf-8-sig')


print("✅ ")