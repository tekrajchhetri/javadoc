Introduction
------------

Markdown is a markup language designed to be simple enough to let anyone write structured documents without the need of a visual editor

I **strongly** encourage you to change the source of the various parts to see what happens (the output will change as 

Basic styles
------------

With this markup you can obtain *simple emhpasis* (usually rendered in italic text), **strong emphasis** (usually rendered in bold text), `source code` text (usually rendered in monospaced text), or ~~strikethrough~~ text (usually rendered with a line through text).

You may use also _this_ or __this__ notation to emphatize text, and you can use all them _**`together`**_ (and you can mix `*` and `_` )

If you look at the source code you may note that
even 
if 
you 
break 
the 
lines,
the text is kept together
in a single paragraph

 Paragraphs are delimited by blank lines, leading and trailing spaces are removed 

You may force a line break with two spaces  
or with a `\`\
at the end of

Links
-----

- You can insert links in text like [this](/tutorial.md)

- You may add a [title](https://agea.github.io/tutorial.md "Markdown Tutorial") to your link (can you see the tooltip?)

- If your link contains spaces you have to write the [link](<http://example.com/a space>) between `<>`

- You can use spaces and markup inside the [link **text**](https://agea.github.io/tutorial.md)

- Long links may decrease source readability, so it's posible to define all links somewhere in the document (the end is a good place) and just reference the [link][tutorial.md], you may also collapse the reference if it matches the link text (example:  [tutorial.md][])

- You may also write directly the link: <https://agea.github.io/tutorial.md>

- It will work also for email addresses: <email@example.com> (you may write vaild email links also using [mailto](mailto:email@example.com) as protocol)


[tutorial.md]: https://agea