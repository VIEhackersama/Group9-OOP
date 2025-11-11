# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
# from itemadapter import ItemAdapter

import csv
import os

class CsvExportPipeline:
    def open_spider(self, spider):
        os.makedirs('data/', exist_ok=True)

        # Mở file CSV khi bắt đầu crawl
        self.file = open('data/muanhadat_data_raw.csv', 'w', newline='', encoding='utf-8-sig')
        self.writer = csv.writer(self.file)

        # Ghi dòng header (tên cột)
        self.writer.writerow([
            'area', 'address', 'street_in_front_of_house','width', 'height',
            'floor_number', 'bedroom_number','bathroom_number','direction','law', 'price'
        ])

    def process_item(self, item, spider):
        # Ghi từng item (mỗi dòng)
        self.writer.writerow([
            item.get('area'),
            item.get('address'),
            item.get('street_in_front_of_house'),
            item.get('width'),
            item.get('height'),
            item.get('floor_number'),
            item.get('bedroom_number'),
            item.get('bathroom_number'),
            item.get('direction'),
            item.get('law'),
            item.get('price')
        ])
        return item

    def close_spider(self, spider):
        # Đóng file khi crawl xong
        self.file.close()
