package solution

import kotlin.math.ceil

/*
Задание:
Необходимо разбить входную строку на смс-ки
То есть написать метод, который принимает строку и максимальную длину смс, а возвращает массив строк сформированный
по следующим правилам:

1) разбивать текст можно только по пробелам
2) если получилось >1 смс-ки то нужно добавить ко всем суффикс вида " 12/123" где 12 номер текущей смс 123 общее число смс
3) длина смс включая суффикс должна быть не больше переданного лимита
4) так как отправка смс платная важно получить минимальное число смс
дополнительно гарантируется что переданную строку можно по всем правилам разбить на смс-ки (например нет слов длиннее
лимита и прочее)
 */

fun String.splitMessageExtension(maxLengthMessage: Int): List<String> {
    val resultList = mutableListOf<String>()
    val message = mutableListOf<String>()
    var wordIndex = 1
    var messageIndex = 1
    var lengthMessage = 0

    if (this.isEmpty()) throw NullPointerException(StringConst.NULL_POINTER_HANDLER.value)

    var numberOfMessages = ceil((this.length.toDouble() / maxLengthMessage.toDouble())).toInt()
    if (numberOfMessages != 1) numberOfMessages =
        ceil(((this.length + 2 * numberOfMessages).toDouble() / maxLengthMessage.toDouble())).toInt()

    val splitText: List<String> = this.split(" ")

    for (value in splitText) {
        try {
            message.add(value)
            lengthMessage += value.length
            if ((
                        lengthMessage + splitText[wordIndex].length + wordIndex.toString().length + 2 +
                        numberOfMessages.toString().length) > maxLengthMessage
            ) {
                resultList.add(message.joinToString(" ") + " ${messageIndex}/$numberOfMessages")
                messageIndex++
                message.clear()
                lengthMessage = 0
            }
            wordIndex++
        } catch (e: RuntimeException) {
            if (numberOfMessages != 1) resultList.add(message.joinToString(" ") + " ${messageIndex}/$numberOfMessages")
            else resultList.add(message.joinToString(" "))
            message.clear()
        }
    }
    return resultList.toList()
}