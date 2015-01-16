# EX_BAD_EBOOKS

> [ERROR] PodError: bad routine gender at corpus (417:3)
>
> –– https://twitter.com/EX_BAD_EBOOKS/status/531778295122362368

[@EX_BAD_EBOOKS](https://twitter.com/EX_BAD_EBOOKS) is a Twitter bot that periodically generates and tweets out cryptic error messages. I made it a few months ago out of frustration with computers and as an experiment in building Twitter bots with Clojure, and since then I've been making small tweaks and improvements on a fairly regular basis.

## Architecture

The project follows a fairly typical structure:

* `src/cryptic/bot.clj` contains the `-main` method that actually launches the bot, using [cronj](https://github.com/zcaudate/cronj) to schedule a repeating task that uses [twitter-api](https://github.com/adamwynne/twitter-api) to tweet a generated error message.
* `src/cryptic/generate.clj` contains the `error` function, which is responsible for actually generating error messages, and a number of helper functions that are used to generate specific parts of each message.
* `src/cryptic/vocabulary.clj` contains lists of nouns, verbs, and two kinds of adjectives. The `cryptic.generate` namespace uses these lists to do its job.
* `test/cryptic/test_vocab.clj` contains tests that are run against the bot's vocabulary. Since words are added to the vocabulary by hand, it's helpful to have an automatic check that ensures none of the words are duplicated.
* `Procfile` is a configuration file that tells [Heroku](https://www.heroku.com/) (on which EX_BAD_EBOOKS is currently hosted) how exactly to start the bot.

## License

[MIT License](http://opensource.org/licenses/MIT). Feel free to use this for anything, but especially for learning purposes or as a base for your own Twitter bot. If you use it to build something cool, please do [tweet at me](https://twitter.com/maxkreminski) about it!
