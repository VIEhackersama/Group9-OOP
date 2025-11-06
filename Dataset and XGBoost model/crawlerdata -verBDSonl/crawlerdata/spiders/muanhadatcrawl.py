import scrapy
from crawlerdata.items import DataPriceItem
import re


class HouseSpider(scrapy.Spider):
    name = "house"
    allowed_domains = ["muanhadat.com.vn"]
    start_urls = ["https://muanhadat.com.vn/thanh-vien/932645807.html?pi=0"]

    def start_requests(self):
        for i in range(0, 6):  # crawl từ pi=0 đến pi=...
            url = f"https://muanhadat.com.vn/thanh-vien/932645807.html?pi={i}"
            yield scrapy.Request(url=url, callback=self.parse_link)

    def parse_link(self, response):
        """Thu thập link của từng bài đăng trên mỗi trang"""
        post_links = response.css('div.re__card-info h3 a::attr(href)').getall()

        for href in post_links:
            url = response.urljoin(href)
            self.logger.info(f"Found link: {url}")
            yield scrapy.Request(url=url, callback=self.parse_detail)


    def extract(self, response, selector):
        value = response.css(selector + "::text").get()
        if value:
            return value.strip()
        return None


    def extract_table(self, response):
        #collecting data from the table
        data = {}
        rows = response.css("div.re__pr-specs-content-item")

        for row in rows:
            key = row.css("span.re__pr-specs-content-item-title::text").get()
            value = row.css("span.re__pr-specs-content-item-value::text").get()
            if key and value:
                data[key.strip()] = value.strip()
        return data

    def price_to_lower(self, s):
        ty = float(re.search(r'(\d+(?:\.\d+)?)\s*t', s).group(1)) if 't' in s else 0
        trieu = float(re.search(r'(\d+(?:\.\d+)?)\s*tr', s).group(1)) if 'tr' in s else 0
        return round(ty + trieu / 1000, 2)

    def incase_error (self, s):
        matches = re.findall(r'\d+', s)
        return int(matches[0]) if matches else 0

    def parse_detail(self, response, **kwargs):
        """Trích xuất dữ liệu chi tiết của từng bài đăng"""
        item = DataPriceItem()

        # 🏷️ Giá
        item['price'] = self.price_to_lower(self.extract(response, "div.re__pr-short-info div:nth-child(1) span.value"))

        # 📍 Địa chỉ
        item['address'] = self.extract(response, "div.re__breadcrumb.js__breadcrumb span:nth-child(5) a span")

        # 📐 Diện tích
        item['area'] = int(re.findall(r'\d+', self.extract(response, "div.re__pr-short-info div:nth-child(2) span.value"))[0])

        # 📋 Dữ liệu trong bảng thông tin
        table_data = self.extract_table(response)

        item['floor_number'] = self.incase_error(str(table_data.get('Số tầng', '')))
        item['bedroom_number'] = self.incase_error(str(table_data.get('Phòng ngủ') or self.extract(response,"div.re__pr-short-info div:nth-child(3) span.value")))
        item['bathroom_number'] = self.incase_error(str(table_data.get('Nhà tắm') or self.extract(response,"div.re__pr-short-info div:nth-child(4) span.value")))
        item['direction'] = table_data.get('Hướng nhà', 'Không xác định')
        item['street_in_front_of_house'] = self.incase_error(str(table_data.get('Đường trước nhà', '')))
        item['height'] = int(re.findall(r'\d+', str(table_data.get('Chiều ngang', '1')))[0])

        width_tmp = str(table_data.get('Chiều dài', '')).strip()
        if not width_tmp:
            width_tmp = round(item['area'] / item['height'], 1)
        else:
            matches = re.findall(r'\d+', width_tmp)
            width_tmp = int(matches[0]) if matches else 0

        item['width'] =  width_tmp
        item['law'] = 1 if table_data.get('Pháp lý') else 0

        yield item
