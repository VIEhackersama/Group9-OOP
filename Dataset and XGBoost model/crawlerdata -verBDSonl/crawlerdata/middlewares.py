# Define here the models for your spider middleware
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals
from scrapy.downloadermiddlewares.useragent import UserAgentMiddleware
import random
from http.cookies import SimpleCookie


# Middleware xoay vòng User-Agent
class RotateUserAgentMiddleware(UserAgentMiddleware):
    user_agents_list = [
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36',
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.3 Safari/605.1.15',
        'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
        'Mozilla/5.0 (Windows NT 10.0; Win64; rv:115.0) Gecko/20100101 Firefox/115.0',
    ]

    def process_request(self, request, spider):
        ua = random.choice(self.user_agents_list)
        request.headers.setdefault('User-Agent', ua)


# Middleware giữ cookie giữa các request
class CookiePersistMiddleware:
    def __init__(self):
        self.cookies = {}

    def process_request(self, request, spider):
        # Nếu có cookies đã lưu, thêm vào request mới
        if self.cookies:
            request.cookies = self.cookies

    def process_response(self, request, response, spider):
        # Lưu lại các cookie mới từ response (nếu có)
        cookie_headers = response.headers.getlist('Set-Cookie')
        for header in cookie_headers:
            cookie = SimpleCookie()
            cookie.load(header.decode('utf-8'))
            for key, morsel in cookie.items():
                self.cookies[key] = morsel.value
        return response


# Spider middleware mặc định
class CrawlerdataSpiderMiddleware:
    @classmethod
    def from_crawler(cls, crawler):
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_spider_input(self, response, spider):
        return None

    def process_spider_output(self, response, result, spider):
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        pass

    async def process_start(self, start):
        async for item_or_request in start:
            yield item_or_request

    def spider_opened(self, spider):
        spider.logger.info("Spider opened: %s" % spider.name)


# Downloader middleware mặc định
class CrawlerdataDownloaderMiddleware:
    @classmethod
    def from_crawler(cls, crawler):
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_request(self, request, spider):
        return None

    def process_response(self, request, response, spider):
        return response

    def process_exception(self, request, exception, spider):
        pass

    def spider_opened(self, spider):
        spider.logger.info("Spider opened: %s" % spider.name)