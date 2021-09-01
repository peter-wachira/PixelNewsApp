package com.droid.newsapiclient.api

import com.droid.newsapiclient.data.api.NewsAPIService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var  service: NewsAPIService
    private lateinit var  server: MockWebServer

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(server.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    private  fun enqueueMockResponse(
            fileName: String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }


    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody =  service.getTopHeadlines("us",1)
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("v2/top-headlines?country=us&page=1&apiKey=2971bdff4e4041eaa48faa6601224dc7")
        }
    }


    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody =  service.getTopHeadlines("us",1).body()
            val articleList  = responseBody!!.articles
            val article = articleList[0]
            assertThat(article.author).isEqualTo("Jacob Kastrenakes")
            assertThat(article.url).isEqualTo("https://www.theverge.com/2021/9/1/22650896/hue-spotify-music-sync-integration")
            assertThat(article.publishedAt).isEqualTo("2021-09-01T07:30:00Z")
        }
    }




}