package site.ymango.user.model

import site.ymango.user.entity.*

fun User.toModel() = UserModel(
    userId = userId,
    phoneNumber = phoneNumber,
    email = email,
    deviceId = deviceId,
    status = status,
    role = role,
    updatedAt = updatedAt,
    createdAt = createdAt,
    userProfile = userProfile?.toModel(),
)

fun UserProfile.toModel() = UserProfileModel(
    userProfileId = userProfileId,
    userId = userId,
    nickname = nickname,
    companyName = companyName,
    gender = gender,
    birthdate = birthdate,
    mbti = mbti,
    preferredMBTI = preferredMBTI,
    sido = sido,
    sigungu = sigungu,
    location = location,
    height = height,
    bodyType = bodyType,
    job = job,
    smoking = smoking,
    drinking = drinking,
    religion = religion,
    introduction = introduction,
    dream = dream,
    loveStyle = loveStyle,
    charm = charm,
    interest = interest,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt,
)