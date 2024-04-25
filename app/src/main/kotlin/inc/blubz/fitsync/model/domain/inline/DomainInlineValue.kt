package inc.blubz.fitsync.model.domain.inline

import kotlinx.datetime.Instant

@JvmInline
value class IndividualId(val value: String) {
    init {
        require(value.isNotBlank()) { "IndividualId cannot have a empty value" }
    }
}

@JvmInline
value class HouseholdId(val value: String) {
    init {
        require(value.isNotBlank()) { "HouseholdId cannot have a empty value" }
    }
}

@JvmInline
value class FirstName(val value: String)

@JvmInline
value class LastName(val value: String)

@JvmInline
value class Phone(val value: String)

@JvmInline
value class Email(val value: String)

@JvmInline
value class CreatedTime(val value: Instant)

@JvmInline
value class LastModifiedTime(val value: Instant)
