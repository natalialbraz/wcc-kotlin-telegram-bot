package bots

import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class WCCBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        //return bot username
        // If bot username is @HelloKotlinBot, it must return
        return "WccNaluBot"
    }

    override fun getBotToken(): String {
        // Return bot token from BotFather
        return "2128890496:AAE8YOgJka7JVD6B-smynkff2refvuFaHcc"
    }

    override fun onUpdateReceived(update: Update?) {
        // We check if the update has a message and the message has text
        val nameSender = update?.message?.from?.firstName
        val chatId = update?.message?.chatId.toString()
        val messageCommand = update?.message?.text

        try {
            if(messageCommand=="/start") {
                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    this.caption = getMessage(messageCommand, nameSender)
                    this.document = InputFile().setMedia("https://media2.giphy.com/media/4oMoIbIQrvCjm/giphy.gif?cid=ecf05e47m6e3bdkc5i1fvd66l5jn268wyy3gzt77d8jnkc52&rid=giphy.gif&ct=g")
                    this.parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            } else {
                val sendDocument = SendMessage().apply {
                    this.chatId = chatId
                    this.text = EmojiParser.parseToUnicode("Escolha não reconhecida :weary:")
                    this.parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
    private fun getMessage(command:String?, nameSender: String?) = when(command){
        "/info" -> EmojiParser.parseToUnicode("Não há informação :disappointed:")
        "/start" -> welcome(nameSender)
        else -> EmojiParser.parseToUnicode("não sei :weary:")
    }
    private fun welcome(nameSender: String?) = EmojiParser.parseToUnicode("""
        :us: *Welcome to EnglishFun, $nameSender * :us: 
        
        :rocket:Escolha uma das opções abaixo para iniciar seu processo de aprendizagem: 
        \/motivation
        \/sons   
        \/dictionary
    """.trimIndent())
}

