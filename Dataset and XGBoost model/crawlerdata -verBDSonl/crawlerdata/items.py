# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class CrawlerdataItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass

class DataPriceItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    area = scrapy.Field()  # dien_tich \ double
    address = scrapy.Field()  # dia_chi \ string

    floor_number = scrapy.Field()  # so_lau (so_tang) \ int
    bedroom_number = scrapy.Field()  # so_phong_ngu \ int
    bathroom_number = scrapy.Field()  #so_nha_tam \ int
    direction = scrapy.Field()  # phuong_huong_nha (nam, bac, dong, tay) \ string
    street_in_front_of_house = scrapy.Field()  # do_rong_duong_truoc_nha \ int
    width = scrapy.Field()  # chieu_dai \ string
    height = scrapy.Field()  # chieu_ngang \ string
    law = scrapy.Field()  # phap_ly \ string

    price = scrapy.Field()  # gia_nha \ double
