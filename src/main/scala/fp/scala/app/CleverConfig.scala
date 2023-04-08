package fp.scala.app

import zio.*
import zio.config.*
import ConfigDescriptor.*
import zio.config.magnolia.{descriptor, Descriptor}

case class CleverEnv(
    PORT: Int,
    APPLICATION_SECRET: String,
    POSTGRESQL_ADDON_HOST: String,
    POSTGRESQL_ADDON_PORT: String,
    POSTGRESQL_ADDON_DB: String,
    POSTGRESQL_ADDON_USER: String,
    POSTGRESQL_ADDON_PASSWORD: String
)

val cleverEnv: ConfigDescriptor[CleverEnv] = descriptor[CleverEnv]
