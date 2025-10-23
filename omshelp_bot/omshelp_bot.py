import os
from dotenv import load_dotenv
from telegram import Update, InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import ApplicationBuilder, CommandHandler, ContextTypes, CallbackQueryHandler, MessageHandler, filters
import PyPDF2

# Загружаем токен из .env
load_dotenv()
TOKEN = os.getenv("TELEGRAM_TOKEN")

VERSION = "v2.0 🥷🤖"

# Загружаем PDF (например для поиска по тарифам)
PDF_PATH = "001.ОМС.pdf"
pdf_file = open(PDF_PATH, "rb")
pdf_reader = PyPDF2.PdfReader(pdf_file)
print(f"✅ Загружено {len(pdf_reader.pages)} страниц PDF")

# Команды бота
async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    keyboard = [
        [InlineKeyboardButton("🧾 Справка", callback_data="help")],
        [InlineKeyboardButton("🔍 Как искать тарифы", callback_data="search_info")],
        [InlineKeyboardButton("📘 Скачать программу госгарантий", callback_data="program")],
        [InlineKeyboardButton("📱 Контакты для Вас", callback_data="contacts")]
    ]
    reply_markup = InlineKeyboardMarkup(keyboard)
    await update.message.reply_text(
        f"Привет! 🤖 Бот запущен и готов к работе. ({VERSION})\nВыбери действие ниже:",
        reply_markup=reply_markup
    )

async def button(update: Update, context: ContextTypes.DEFAULT_TYPE):
    query = update.callback_query
    await query.answer()

    if query.data == "help":
        text = "📄 Команды:\n/start — запустить бота\n/help — справка"
    elif query.data == "search_info":
        text = "🧾 Здесь ты можешь искать тарифы по МКБ. Пример ввода: ВМП С-67"
    elif query.data == "program":
        text = "📘 Программа государственных гарантий ОМС"
        # Отправка PDF
        with open("Программа_госгарантий.pdf", "rb") as f:
            await query.message.reply_document(f, caption="📘 Программа государственных гарантий ОМС")
        return
    elif query.data == "contacts":
        text = "@MSL72Rph 🥷 + TARS 🤖 (внедрение AI в медицине)"

    await query.message.reply_text(text)

async def search(update: Update, context: ContextTypes.DEFAULT_TYPE):
    query_text = update.message.text.strip().upper()
    results = []
    for i, page in enumerate(pdf_reader.pages):
        if query_text in page.extract_text().upper():
            results.append(f"Страница {i + 1}")
    if results:
        await update.message.reply_text("Найдено:\n" + "\n".join(results))
    else:
        await update.message.reply_text("Ничего не найдено 😔")

app = ApplicationBuilder().token(TOKEN).build()

app.add_handler(CommandHandler("start", start))
app.add_handler(CommandHandler("help", start))
app.add_handler(CallbackQueryHandler(button))
app.add_handler(MessageHandler(filters.TEXT & ~filters.COMMAND, search))

print("✅ Бот запущен... Ждёт сообщений.")
app.run_polling()
