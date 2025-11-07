BOT_NAME = 'crawlerdata'

SPIDER_MODULES = ['crawlerdata.spiders']
NEWSPIDER_MODULE = 'crawlerdata.spiders'

# 🧭 Không tuân robots.txt
ROBOTSTXT_OBEY = False

# 🕐 Giới hạn tốc độ và song song
DOWNLOAD_DELAY = 1.5
CONCURRENT_REQUESTS_PER_DOMAIN = 2

# 🧠 Fake User-Agent xoay vòng
DOWNLOADER_MIDDLEWARES = {
    'crawlerdata.middlewares.RotateUserAgentMiddleware': 400,
    'crawlerdata.middlewares.CookiePersistMiddleware': 401,
    'scrapy.downloadermiddlewares.useragent.UserAgentMiddleware': None,
}

DEFAULT_REQUEST_HEADERS = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 '
                  '(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    'Accept-Language': 'vi-VN,vi;q=0.9,en;q=0.8',
    'Referer': 'https://www.google.com/',
    'Connection': 'keep-alive',
}

ITEM_PIPELINES = {
    'crawlerdata.pipelines.CsvExportPipeline': 300,
}

FEED_EXPORT_ENCODING = "utf-8"