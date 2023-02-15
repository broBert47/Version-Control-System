package svcs

import java.io.File
import java.security.MessageDigest

val map = mapOf(
    "config" to "Get and set a username.",
    "add" to "Add a file to the index.",
    "log" to "Show commit logs.",
    "commit" to "Save changes.",
    "checkout" to "Restore a file."
)

fun main(args: Array<String>) {
    createVcsFolder()

    val config = File("./vcs/config.txt")
    config.createNewFile()

    val add = File("./vcs/index.txt")
    add.createNewFile()

    val log = File("./vcs/log.txt")
    log.createNewFile()

    val commits = File("./vcs/commits")
    commits.mkdir()


    when {
        args.isEmpty() || args.first() == "--help" -> {
            println("These are SVCS commands:")
            for ((key, value) in map) {
                println(key + " ".repeat(12 - key.length) + value)
            }
        }

        args[0] !in map.keys -> println("'${args[0]}' is not a SVCS command.")
        "config" in args -> setupConfig(config, args)
        "add" in args -> trackingFiles(add, args, map)
        "log" in args -> showLog(log)
        "commit" in args -> startCommit(args)
        "checkout" in args -> checkoutFiles(args)
    }
}

fun createVcsFolder() {
    val vcsFile = File("vcs")
    vcsFile.mkdir()
}

fun setupConfig(config: File, args: Array<String>) {
    var name: String = ""
    when {
        config.length() == 0L && args.size == 1 -> println("Please, tell me who you are.")
        args.size == 2 -> {
            name = args[1]
            config.writeText("The username is $name.")
            println(config.readText())
        }

        config.length() != 0L && args.size == 1 -> println(config.readText())
    }
}

fun trackingFiles(index: File, args: Array<String>, map: Map<String, String>) {

    when {
        index.length() == 0L && args.size == 1 -> println(map[args[0]])
        index.length() != 0L && args.size == 1 -> println(index.readText())
        index.length() == 0L && args.size == 2 -> {
            val file = File(args[1])
            if (file.exists()) {
                index.writeText(
                    """
                Tracked files:
                ${args[1]}
            """.trimIndent()
                )
                println("The file '${args[1]}' is tracked.")
            } else {
                println("Can't find '${args[1]}'.")
            }
        }

        index.length() != 0L && args.size == 2 -> {
            val file = File(args[1])
            if (file.exists()) {
                index.appendText("\n${args[1]}")
                println("The file '${args[1]}' is tracked.")
            } else {
                println("Can't find '${args[1]}'.")
            }
        }

    }
}

fun showLog(log: File) {
    if (log.length() == 0L) {
        println("No commits yet.")
    } else if (log.length() != 0L) {
        println(log.readText())
    }
}

fun startCommit(args: Array<String>) {


    if (args.size == 1) {
        println("Message was not passed.")
    } else if (args.size == 2) {
        val commitMessage = args[1]
        val commitHash = generateHash()
        val mainFolder = File("./vcs/commits/")

        //Gledamo da li postoji folder s imenom hasha u Commits folderu.
        // Ako da, znači da nemamo šta commitat i prekidamo program.
        mainFolder.listFiles()?.forEach { folder ->
            if (folder.name == commitHash) {
                println("Nothing to commit.")
                return
            }
        }

        for (i in trackedFiles()) {
            val fileObject = File(i)
            val newFile = File("vcs/commits/$commitHash/$i")
            fileObject.copyTo(newFile)
        }

        val configFile = getConfigFile()
        val splitConfig = configFile.readText().split(" ")
        val name = splitConfig.last().dropLast(1)

        val logFile = getLogFile()
        val readPreviousLog = logFile.readText()
        logFile.writeText(
            """
                |commit $commitHash
                |Author: $name
                |$commitMessage
                |
            """.trimMargin()
        )
        logFile.appendText(readPreviousLog)

        println("Changes are committed.")
    }
}

fun checkoutFiles(args: Array<String>) {
    if (args.size == 1) {
        println("Commit id was not passed.")
    } else {
        val commitFolderName = args[1]

        val mainFolder = File("./vcs/commits/")
        val rootFolder = File("/")

        mainFolder.listFiles()?.forEach { folder ->
            if (folder.name == commitFolderName) {
                val hashFolder = File("./vcs/commits/$commitFolderName/")

                hashFolder.listFiles()?.forEach { file ->
                    rootFolder.listFiles()?.forEach { rootfile ->
                        if(rootfile.name.startsWith("file")){
                            file.delete()
                        }
                    }

                   try{
                        file.copyTo(rootFolder)
                    } catch (e: FileAlreadyExistsException){
                        val text = file.readText()
                        val noviFile = File(file.name)
                        noviFile.writeText(text)
                    }

                }
                println("Switched to commit ${commitFolderName}.")
                return
            }
        }

        println("Commit does not exist.")
    }




}


fun getLogFile(): File {
    return File("./vcs/log.txt")
}

fun getConfigFile(): File {
    return File("./vcs/config.txt")
}


fun generateHash(): String {

    val trackedFileNames = trackedFiles()

    // napravimo temp file u koji cemo ubaciti sav sadrzaj svih trackanih fileova
    val tempFile = File("vcs/tempFile.txt")
    tempFile.createNewFile()

    // iteriramo po listi imena trackanih fileova i citamo ih i kopiramo sadrzaj u tempFile
    trackedFileNames.forEach { fileName ->
        val currentFile = File(fileName)
        val content = currentFile.readText()
        tempFile.appendText(content)
    }

    // radimo hash od temp filea
    val readText = tempFile.readText().toByteArray()
    val hash = sha256(readText)

    // obrisati temp file
    tempFile.delete()

    return hash
}

fun sha256(input: ByteArray): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(input)
    return hash.fold("") { str, it -> str + "%02x".format(it) }
}


fun trackedFiles(): List<String> {
    // izvlacimo iz index.txt imena svih trackanih fileova
    val trackedFileNames = mutableListOf<String>()
    val index = File("vcs/index.txt")
    index.forEachLine {
        if (it != "Tracked files:") {
            trackedFileNames.add(it)
        }
    }
    return trackedFileNames
}


fun testiranje() {
    val file = File("test1.txt")
    file.createNewFile()
    file.writeText("this is a test")

    val logFile = getLogFile()
    logFile.createNewFile()

    val configFile = File("vcs/config.txt")
    configFile.createNewFile()
    configFile.writeText("The username is John.")

    val lista: Array<String> = arrayOf("add", "test1.txt")
    val add = File("vcs/index.txt")
    add.createNewFile()
    trackingFiles(add, lista, map)

    val lista2 = arrayOf("commit", "testni commit")
    startCommit(lista2)

    file.writeText("dodatak")
    val lista3 = arrayOf("commit", "novi commit")
    startCommit(lista3)

    checkoutFiles(arrayOf("checkout", "2e99758548972a8e8822ad47fa1017ff72f06f3ff6a016851f45c398732bc50c"))

    //file.delete()
    //add.delete()
    return
}
